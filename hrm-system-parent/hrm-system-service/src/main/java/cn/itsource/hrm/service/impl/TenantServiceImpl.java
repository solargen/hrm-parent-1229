package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.controller.vo.RegisterVo;
import cn.itsource.hrm.domain.*;
import cn.itsource.hrm.mapper.*;
import cn.itsource.hrm.service.ITenantService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    private TenantMealMapper tenantMealMapper;
    @Autowired
    private MealPermissionMapper mealPermissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    /**
     * 租户注册
     * @param registerVo
     */
    //@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    @Transactional
    @Override
    public void register(RegisterVo registerVo) {
        //租户表(返回主键) - 自动返回主键,主键返回到了对象中
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(registerVo,tenant);
        baseMapper.insert(tenant);
        //租户套餐中间表
        TenantMeal tenantMeal = new TenantMeal();
        tenantMeal.setTenantId(tenant.getId());
        tenantMeal.setMealId(registerVo.getMealId());
        tenantMealMapper.insert(tenantMeal);
        //角色(ADMIN)(返回主键)
        Role role = new Role();
        role.setName("租户管理员");
        role.setSn("ADMIN");
        role.setTenantId(tenant.getId());
        roleMapper.insert(role);
        //角色权限中间表
        // 1、查询当前套餐的所有权限
        List<MealPermission> mealPermissions = mealPermissionMapper.selectList(
                new QueryWrapper<MealPermission>().eq("meal_id", registerVo.getMealId())
        );
        List<Long> permissionIds = mealPermissions.stream()
                .map(MealPermission::getPermissionId)
                .collect(Collectors.toList());
        // 2、角色权限中间表批量插入
        rolePermissionMapper.insertBatch(role.getId(),permissionIds);
        //用户表(返回主键)
        Employee employee = new Employee();
        employee.setUsername(registerVo.getUsername());
        employee.setPassword(registerVo.getPassword());
        employee.setInputTime(System.currentTimeMillis());
        employee.setState(1);
        employee.setTenantId(tenant.getId());
        employeeMapper.insert(employee);
        //用户角色中间表
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setEmployeeId(employee.getId());
        employeeRole.setRoleId(role.getId());
        employeeRoleMapper.insert(employeeRole);
    }
}
