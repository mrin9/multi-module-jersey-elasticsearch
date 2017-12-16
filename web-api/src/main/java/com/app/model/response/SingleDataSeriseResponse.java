//This is a common http response model for providing data series

package com.app.model.response;

import com.app.model.stats.SingleSerise;
import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class SingleDataSeriseResponse extends MultiMessageResponse {
    private List<SingleSerise> items;
}
