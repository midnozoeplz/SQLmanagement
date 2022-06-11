# SQLmanagement
一个字：摆

采用Springboot + SpringSecurity + redis + Mybatis-plus + jwt

前端vue： npm install + npm run dev启动
后端直接run就行


登录账号：
   
    管理员账号：admin 密码：admin
        admin功能附加：可以在购物平台添加删修改除商品

    普通账号： 注册页面注册
             只支持购物功能
        
前端项目： vueproject

注意：
 上传的货物图片因为必须储存在本地，而不能在项目target路径下，<br>
所以git下来需要修改2个地方的文件路径：

                    1.ImageController下注释处
                    2.GoWebMvcConfigurerAdapter下的注释处
