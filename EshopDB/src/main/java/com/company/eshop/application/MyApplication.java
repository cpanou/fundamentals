package com.company.eshop.application;

import com.company.eshop.controllers.ProductResource;
import com.company.eshop.controllers.UserResource;
import com.company.eshop.providers.ApplicationExceptionMapper;
import com.company.eshop.providers.WebApplicationExceptionMapper;
import com.company.eshop.repository.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/eshop")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet set = new HashSet<Class<?>>();
        set.add(JacksonJsonProvider.class);

        set.add(ProductResource.class);
        set.add(UserResource.class);

        set.add(WebApplicationExceptionMapper.class);
        set.add(ApplicationExceptionMapper.class);

        return set;
    }

    public MyApplication() {
        super();
        DataBaseUtils.registerJDBCDriver();

        ProductRepository.init();
        OrderRepository.init();
        CartStore.init();
    }

}
