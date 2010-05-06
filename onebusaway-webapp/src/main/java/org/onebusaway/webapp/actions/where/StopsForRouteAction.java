package org.onebusaway.webapp.actions.where;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.onebusaway.exceptions.ServiceException;
import org.onebusaway.transit_data.model.NameBean;
import org.onebusaway.transit_data.model.RouteBean;
import org.onebusaway.transit_data.model.StopBean;
import org.onebusaway.transit_data.model.StopGroupBean;
import org.onebusaway.transit_data.model.StopGroupingBean;
import org.onebusaway.transit_data.model.StopsForRouteBean;
import org.onebusaway.transit_data.model.TransitDataConstants;
import org.onebusaway.utility.text.NaturalStringOrder;

import edu.washington.cs.rse.collections.CollectionsLibrary;

public class StopsForRouteAction extends AbstractWhereAction {

  private static final long serialVersionUID = 1L;

  private static final StopNameComparator _stopNameComparator = new StopNameComparator();

  private String _id;

  private RouteBean _route;

  private List<StopBean> _stops = new ArrayList<StopBean>();

  private List<NameBean> _directionNames = new ArrayList<NameBean>();

  private int _groupIndex = -1;

  public void setId(String id) {
    _id = id;
  }

  public void setGroupIndex(int groupIndex) {
    _groupIndex = groupIndex;
  }

  public RouteBean getRoute() {
    return _route;
  }

  public List<StopBean> getStops() {
    return _stops;
  }

  public List<NameBean> getDirectionNames() {
    return _directionNames;
  }

  @Override
  @Actions( {
      @Action(value = "/where/iphone/stops-for-route"),
      @Action(value = "/where/text/stops-for-route")})
  public String execute() throws ServiceException {

    if (_id == null || _id.length() == 0)
      return INPUT;

    _route = _transitDataService.getRouteForId(_id);
    StopsForRouteBean stopsForRoute = _transitDataService.getStopsForRoute(_id);

    Map<String, StopGroupingBean> groupingsByType = CollectionsLibrary.mapToValue(
        stopsForRoute.getStopGroupings(), "type", String.class);
    StopGroupingBean byDirection = groupingsByType.get(TransitDataConstants.STOP_GROUPING_TYPE_DIRECTION);

    if (_groupIndex == -1) {
      if (byDirection != null) {
        for (StopGroupBean group : byDirection.getStopGroups())
          _directionNames.add(group.getName());
      }
      _stops = stopsForRoute.getStops();
      Collections.sort(_stops, _stopNameComparator);
    } else {
      if (byDirection == null)
        return INPUT;
      List<StopGroupBean> groups = byDirection.getStopGroups();
      if (_groupIndex < 0 && groups.size() <= _groupIndex)
        return INPUT;
      Map<String, StopBean> stopById = CollectionsLibrary.mapToValue(
          stopsForRoute.getStops(), "id", String.class);

      StopGroupBean stopGroup = groups.get(_groupIndex);
      _stops = new ArrayList<StopBean>();
      for (String stopId : stopGroup.getStopIds())
        _stops.add(stopById.get(stopId));
    }
    return SUCCESS;
  }

  private static class StopNameComparator implements Comparator<StopBean> {

    @Override
    public int compare(StopBean o1, StopBean o2) {
      return NaturalStringOrder.compareNatural(o1.getName(), o2.getName());
    }
  }
}
