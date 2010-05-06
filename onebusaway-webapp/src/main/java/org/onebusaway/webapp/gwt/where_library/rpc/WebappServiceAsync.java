/*
 * Copyright 2008 Brian Ferris
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.onebusaway.webapp.gwt.where_library.rpc;

import java.util.Date;
import java.util.List;

import org.onebusaway.transit_data.model.AgencyWithCoverageBean;
import org.onebusaway.transit_data.model.ListBean;
import org.onebusaway.transit_data.model.RouteBean;
import org.onebusaway.transit_data.model.RoutesAndStopsBean;
import org.onebusaway.transit_data.model.RoutesBean;
import org.onebusaway.transit_data.model.SearchQueryBean;
import org.onebusaway.transit_data.model.StopBean;
import org.onebusaway.transit_data.model.StopScheduleBean;
import org.onebusaway.transit_data.model.StopWithArrivalsAndDeparturesBean;
import org.onebusaway.transit_data.model.StopsBean;
import org.onebusaway.transit_data.model.StopsForRouteBean;
import org.onebusaway.transit_data.model.TripDetailsBean;
import org.onebusaway.transit_data.model.TripsForBoundsQueryBean;
import org.onebusaway.transit_data.model.oba.LocalSearchResult;
import org.onebusaway.transit_data.model.oba.MinTransitTimeResult;
import org.onebusaway.transit_data.model.oba.MinTravelTimeToStopsBean;
import org.onebusaway.transit_data.model.oba.OneBusAwayConstraintsBean;
import org.onebusaway.transit_data.model.oba.TimedPlaceBean;
import org.onebusaway.transit_data.model.tripplanner.TripPlanBean;
import org.onebusaway.transit_data.model.tripplanner.TripPlannerConstraintsBean;
import org.onebusaway.users.client.model.UserBean;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface WebappServiceAsync extends RemoteService {

  public static final String SERVICE_PATH = "/services/webapp";

  public static WebappServiceAsync SERVICE = GWT.create(WebappService.class);

  public void getCurrentUser(AsyncCallback<UserBean> callback);

  public void setDefaultLocationForUser(String locationName, double lat,
      double lon, AsyncCallback<UserBean> callback);

  public void clearDefaultLocationForUser(AsyncCallback<UserBean> callback);

  public void getAgencies(AsyncCallback<List<AgencyWithCoverageBean>> callback);

  public void getRoutes(SearchQueryBean query,
      AsyncCallback<RoutesBean> callback);

  public void getRoutesAndStops(SearchQueryBean query,
      AsyncCallback<RoutesAndStopsBean> callback);

  public void getRouteForId(String routeId, AsyncCallback<RouteBean> callback);

  public void getStopsForRoute(String routeId,
      AsyncCallback<StopsForRouteBean> callback);

  public void getStops(SearchQueryBean query, AsyncCallback<StopsBean> callback);

  public void getStop(String stopId, AsyncCallback<StopBean> callback);

  public void getArrivalsByStopId(String stopId,
      AsyncCallback<StopWithArrivalsAndDeparturesBean> callback);

  public void getScheduleForStop(String stopId, Date date,
      AsyncCallback<StopScheduleBean> schedule);

  public void getTripsForBounds(TripsForBoundsQueryBean query,
      AsyncCallback<ListBean<TripDetailsBean>> callback);

  public void getTripsBetween(double latFrom, double lonFrom, double latTo,
      double lonTo, TripPlannerConstraintsBean constraints,
      AsyncCallback<List<TripPlanBean>> callback);

  public void getMinTravelTimeToStopsFrom(double lat, double lon,
      OneBusAwayConstraintsBean constraints, int timeSegmentSize,
      AsyncCallback<MinTransitTimeResult> callback);

  public void getLocalPathsToStops(OneBusAwayConstraintsBean constraints,
      MinTravelTimeToStopsBean travelTimes,
      List<LocalSearchResult> localResults,
      AsyncCallback<List<TimedPlaceBean>> callback);
}
