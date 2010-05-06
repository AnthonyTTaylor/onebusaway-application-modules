package org.onebusaway.webapp.actions.sms;

import java.util.ArrayList;
import java.util.List;

import org.onebusaway.exceptions.ServiceException;
import org.onebusaway.presentation.model.BookmarkWithStopsBean;
import org.onebusaway.presentation.services.BookmarkPresentationService;
import org.onebusaway.users.client.model.BookmarkBean;
import org.onebusaway.users.model.properties.RouteFilter;
import org.onebusaway.users.services.BookmarkException;
import org.springframework.beans.factory.annotation.Autowired;

public class CommandBookmarksAction extends AbstractTextmarksAction {

  private static final long serialVersionUID = 1L;

  private BookmarkPresentationService _bookmarkPresentationService;

  private String _arg;

  private List<BookmarkWithStopsBean> _bookmarks = new ArrayList<BookmarkWithStopsBean>();

  private List<String> _stopIds;

  @Autowired
  public void setBookmarkPresentationService(
      BookmarkPresentationService bookmarkPresentationService) {
    _bookmarkPresentationService = bookmarkPresentationService;
  }

  public List<BookmarkWithStopsBean> getBookmarks() {
    return _bookmarks;
  }

  public void setArg(String arg) {
    _arg = arg;
  }

  public List<String> getStopIds() {
    return _stopIds;
  }

  @Override
  public String execute() throws ServiceException, BookmarkException {

    if (_arg != null && _arg.length() > 0) {

      if (_arg.startsWith("add")) {
        List<String> lastSelectedStopIds = _currentUser.getLastSelectedStopIds();
        if (!lastSelectedStopIds.isEmpty()) {
          String name = _bookmarkPresentationService.getNameForStopIds(lastSelectedStopIds);
          _currentUserService.addStopBookmark(name, lastSelectedStopIds,
              new RouteFilter());
        }
        return "added";
      }

      if (_arg.startsWith("delete")) {
        int index = _arg.indexOf(' ');
        if (index == -1)
          return INPUT;
        int bookmarkIndex = Integer.parseInt(_arg.substring(index + 1).trim()) - 1;
        _currentUserService.deleteStopBookmarks(bookmarkIndex);
        return "deleted";
      }

      if (_arg.matches("\\d+")) {
        int index = Integer.parseInt(_arg) - 1;

        List<BookmarkBean> bookmarks = _currentUser.getBookmarks();
        if (index < 0 || index >= bookmarks.size())
          return INPUT;

        BookmarkBean bookmark = bookmarks.get(index);
        _stopIds = bookmark.getStopIds();

        return "arrivals-and-departures";
      }
    }

    _bookmarks = _bookmarkPresentationService.getBookmarksWithStops(_currentUser.getBookmarks());

    return SUCCESS;
  }

  public String getBookmarkName(BookmarkWithStopsBean bookmark) {
    return _bookmarkPresentationService.getNameForBookmark(bookmark);
  }
}
