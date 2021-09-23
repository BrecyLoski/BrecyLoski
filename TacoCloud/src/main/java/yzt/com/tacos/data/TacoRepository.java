package yzt.com.tacos.data;

import yzt.com.tacos.Taco;
import org.springframework.data.repository.CrudRepository;

/* CrudRepository<Taco, Long>:
* 指定Taco实体,及其ID类型
* */
public interface TacoRepository extends CrudRepository<Taco, Long> {

}
