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
/**
 * 
 */
package org.onebusaway.webapp.gwt.where_library.view.constraints;

import org.onebusaway.users.client.model.UserBean;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class DefaultOperationWidget extends Composite {

  private static MyUiBinder _uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  FlowPanel _defaultSearchLocationPanel;

  public DefaultOperationWidget() {
    initWidget(_uiBinder.createAndBindUi(this));
  }

  public void addDefaultSearchLocationLink(UserBean user) {
    DefaultSearchLocationWidget widget = new DefaultSearchLocationWidget(
        user.getDefaultLocationName());
    _defaultSearchLocationPanel.clear();
    _defaultSearchLocationPanel.add(widget);
  }

  interface MyUiBinder extends UiBinder<Widget, DefaultOperationWidget> {
  }
}