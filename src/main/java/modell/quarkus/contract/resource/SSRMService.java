package modell.quarkus.contract.resource;


import com.bmw.approve.tap.service.modules.ssrm.datamapper.BaseDataMapper;
import com.bmw.approve.tap.service.modules.ssrm.provider.SSRMProvider;
import com.bmw.approve.tap.service.modules.ssrm.request.EnterpriseGetRowsRequest;
import com.bmw.approve.tap.service.modules.ssrm.response.EnterpriseGetRowsResponse;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Dependent
public class SSRMService<T> {

  public static final Integer MIN_ROW = 0;
  public static final Integer MAX_ROW = 1000;

  protected final Class<T> type;

  @Inject
  public SSRMService(InjectionPoint ip) {
    this(((Class<T>) (((ParameterizedType) ip.getType()).getActualTypeArguments()[0])));
  }

  public SSRMService(Class<T> type) {
    this.type = type;
  }

  public EnterpriseGetRowsResponse requestRows(
      EnterpriseGetRowsRequest request,
      SSRMProvider<T> service,
      BaseDataMapper<T> mapper,
      Object... parameters) {
    final int startRow = Helper.requireNonNullElse(request, EnterpriseGetRowsRequest::getStartRow, MIN_ROW);
    final int endRow = Helper.requireNonNullElse(request, EnterpriseGetRowsRequest::getEndRow, MAX_ROW);
    final int maxResults = Math.abs(endRow - startRow);
    final List<T> data = service
        .find(startRow, maxResults, request.getSortModel(), request.getFilterModel(), parameters);
    final Long lastRow = service.count(request.getFilterModel(), parameters);
    return buildResponse(data, lastRow, mapper);
  }

  private EnterpriseGetRowsResponse buildResponse(List<T> data, Long lastRow, BaseDataMapper<T> mapper) {
    return new EnterpriseGetRowsResponse(mapper.map(data), lastRow, null);
  }

}
