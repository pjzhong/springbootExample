package com.pjzhong;

import com.pjzhong.entity.FooProperties;
import com.pjzhong.entity.PostInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        Binder binder = Binder.get(context.getEnvironment());

        FooProperties foo = binder.bind("com.pjzhong", Bindable.of(FooProperties.class)).get();
        System.out.println(foo.getFoo());

        List<String> post = binder.bind("com.pjzhong.post", Bindable.listOf(String.class)).get();
        System.out.println(post);

        List<PostInfo> posts = binder.bind("com.pjzhong.posts", Bindable.listOf(PostInfo.class)).get();
        System.out.println(posts);


        // 读取配置
        System.out.println(context.getEnvironment().containsProperty("com.pjzhong.database-platform"));
        System.out.println(context.getEnvironment().containsProperty("com.pjzhong.databasePlatform"));

    }
}
