package br.com.Kostylll.mapper;

import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O,D> D parseObject(O origin, Class<D> desination) {
        return mapper.map(origin, desination);
    }

    public static <O,D> List<D> parseListObject(List<O> origin, Class<D> desination) {

        List<D> destinationObjects = new ArrayList<D>();

        for(O o : origin) {
           destinationObjects.add(mapper.map(o, desination));
        }
        return destinationObjects;
    }

}
