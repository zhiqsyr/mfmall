package org.zhiqsyr.mfmall.web.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhiqsyr
 * @since 17/4/20
 */
@RestController
@RequestMapping("sample")
public class SampleController {

    @GetMapping
    public Object sample() {
        Map<String, Object> result = Maps.newHashMap();
        result.put("name", "dongbz");
        result.put("job", "engineer");

        return result;
    }

}
