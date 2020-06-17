package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.Employee;
import cn.itsource.hrm.mapper.EmployeeMapper;
import cn.itsource.hrm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {
}
