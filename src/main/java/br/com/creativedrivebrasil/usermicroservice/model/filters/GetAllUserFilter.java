package br.com.creativedrivebrasil.usermicroservice.model.filters;

import br.com.creativedrivebrasil.usermicroservice.shared.UserTypeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllUserFilter {

	public String name;
	public String email;
	public String address;
	public String telephone;
	public UserTypeDTO type;
	
	public OrderType order;
	public Integer pageSize;
	public Integer pageOffset;
	
}
