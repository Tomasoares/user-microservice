package br.com.creativedrivebrasil.usermicroservice.model.filters;

import br.com.creativedrivebrasil.usermicroservice.shared.UserTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
