package br.com.creativedrivebrasil.usermicroservice.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import br.com.creativedrivebrasil.usermicroservice.model.filters.GetAllUserFilter;
import br.com.creativedrivebrasil.usermicroservice.model.filters.OrderType;
import br.com.creativedrivebrasil.usermicroservice.mongodb.documents.User;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;
    
	@Override
	public List<User> getPage(GetAllUserFilter filter) {
		filter = initializeDefaultFiltering(filter);

		Query query = new Query();
		
		if (filter.order != null) {
			Direction orderBy = filter.order == OrderType.ASC? Direction.ASC : Direction.DESC;
			query.with(new Sort(orderBy, "name"));
		}
		
		if (filter.pageSize != null || filter.pageOffset != null) {
			int pageSize = filter.pageSize != null? filter.pageSize : 10;
			int pageOffset = filter.pageOffset != null? filter.pageOffset : 0;
			
			query.with(PageRequest.of(pageOffset, pageSize));
		}
		
		if (filter.name != null) {
			query.addCriteria(Criteria.where("name").regex(filter.name));
		}
		
		if (filter.email != null) {
			query.addCriteria(Criteria.where("email").regex(filter.email));
		}
		
		if (filter.address != null) {
			query.addCriteria(Criteria.where("address").is(filter.address));
		}
		
		if (filter.telephone != null) {
			query.addCriteria(Criteria.where("telephone").is(filter.telephone));
		}
		
		if (filter.type != null) {
			query.addCriteria(Criteria.where("idUserType").is(filter.type.getId()));
		}
		
		return mongoTemplate.find(query, User.class);
	}

	private GetAllUserFilter initializeDefaultFiltering(GetAllUserFilter filter) {
		if (filter == null) {
			filter = new GetAllUserFilter();
		}
		
		if (filter.pageSize == null) {
			filter.pageSize = 10;
		}
		
		if (filter.pageOffset == null) {
			filter.pageOffset = 0;
		}
		
		if (filter.order == null) {
			filter.order = OrderType.ASC;
		}
		return filter;
	}

}
