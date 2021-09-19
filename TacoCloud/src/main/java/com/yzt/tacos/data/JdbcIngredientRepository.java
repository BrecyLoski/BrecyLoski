package com.yzt.tacos.data;

import com.yzt.tacos.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbc; //用来执行数据库查询和插入操作

    @Autowired
    // 标注为构造器, 将JdbcTemplate注入进来
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc; //将JdbcTemplate赋值给实例变量jdbc
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient",
                this::mapRowToIngredient);
        /*
        * query()会接受将要执行的SQL以及Spring RowMapper的一个实现(mapRowToIngredient):
        * 用来将结果集中的每行数据映射为一个对象
        * */
    }

    @Override
    public Ingredient findById(String id) {
        return jdbc.queryForObject(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient, id);
        /*
        * queryForObject()运行方式与query()运行方式类似, 只不过返回的是一个对象;
        * findAll()与findById()的RowMapper参数都是通过对mapRowToIngredient()方法引用指定
        * 也可以使用显示RowMapper实现:
        * public Ingredient findById(String id) {
        *   return jdbc.queryForObject(
        *       "select id, name, type from Ingredient where id = ?",
        *       new RowMapper<Ingredient>() {
        *           public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException{
        *               return new Ingredient(
        *                   rs.getString("id"),
        *                   rs.getString("name"),
        *                   Ingredient.Type.valueOf(rs.getString("Type")));
        *           };
        *       }, id);
        * }
        * */
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        // update()执行向数据库写入或更新数据的查询语句
        jdbc.update(
                "insert into Ingredient(id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
