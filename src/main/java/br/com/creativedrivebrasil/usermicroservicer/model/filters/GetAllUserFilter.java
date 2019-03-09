package br.com.creativedrivebrasil.usermicroservicer.model.filters;

import br.com.creativedrivebrasil.usermicroservicer.dto.UserTypeDTO;
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
