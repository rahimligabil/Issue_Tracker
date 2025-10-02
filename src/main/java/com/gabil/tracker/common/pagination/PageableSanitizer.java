package com.gabil.tracker.common.pagination;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableSanitizer {
  private PageableSanitizer() {}

  private static final Set<String> ALLOWED_SORT = Set.of(
      "id", "createdAt", "updatedAt", "priority", "status"
  );

  private static final int MAX_PAGE_SIZE = 100;

  public static Pageable sanitize(final Pageable raw) {
	  
	  int safePage = Math.max(0, raw.getPageNumber());
	  
	  int size = raw.getPageSize();
	  
	  int safeSize = Math.min(MAX_PAGE_SIZE,Math.max(1, size));
	  
	  
	  List<Sort.Order> safeOrders = new ArrayList<>();
	  
	  for(Sort.Order o : raw.getSort()) {
		  if(ALLOWED_SORT.contains(o.getProperty())) {
			  safeOrders.add(o);
		  }
	  }
	  
	  Sort safeSort;
	  
	  if(safeOrders.isEmpty()) {
		  safeSort = Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("id"));
		  
	  }
	  else {
		   boolean hasId = safeOrders.stream().anyMatch(o -> o.getProperty().equals("id"));
	      if (!hasId) safeOrders.add(Sort.Order.desc("id"));
	      safeSort = Sort.by(safeOrders);
	  }
  
	  
	  return PageRequest.of(safePage, safeSize, safeSort);
  
  	}
  }