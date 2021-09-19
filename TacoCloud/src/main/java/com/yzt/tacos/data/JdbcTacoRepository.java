package com.yzt.tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yzt.tacos.Ingredient;
import com.yzt.tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository{

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
         /* save()首先调用私有的saveTacoInfo() --该方法返回一个taco ID;
         * 再调用saveIngredientToTaco()来保存配料信息
         * */
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for(Ingredient ingredient : taco.getIngredients()){
            saveIngredientToTaco(ingredient, tacoId);
        }

        return taco;
    }

    // 获取 taco Id:
    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        /* 创建 PreparedStatementCreator:
        * 首先创建 PreparedStatementCreatorFactory,并将要执行的SQL传递给它, 同时包含查询参数类型;
        * 然后调用 工厂类的newPreparedStatementCreator(), 并将查询参数所需的值传递进来;
        * */
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into Taco (name, createdAt) values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                new Timestamp(taco.getCreatedAt().getTime())
                        )
                );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        /* update()需要接受一个PreparedStatementCreator和一个KeyHolder
        * KeyHolder将会提供生成的TacoID
        * PreparedStatementCreator提供执行的SQL, 参数类型, 值
        * 为了使用该方法必须创建一个PreparedStatementCreator
        * */

        return keyHolder.getKey().longValue(); // 返回taco的ID
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId){
        /* 这个update()无法获取到数据库生成的taco ID, 其以参数形式传入;
        * 这里的update()以更简单的形式将配料信息保存到Taco_Ingredients表中;
        * */
        jdbc.update(
                "insert into Taco_Ingredients(taco, ingredient) values(?, ?)",
                tacoId, ingredient.getId()
        );
    }
}
