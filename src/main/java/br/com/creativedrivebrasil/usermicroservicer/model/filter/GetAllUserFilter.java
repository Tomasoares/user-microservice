package br.com.creativedrivebrasil.usermicroservicer.model.filter;

import br.com.creativedrivebrasil.usermicroservicer.model.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllUserFilter {

	public String name;
	public String email;
	public String address;
	public String telephone;
	public UserType type;
	
	public OrderType order;
	public Integer pageSize;
	public Integer pageIndex;
	
}
