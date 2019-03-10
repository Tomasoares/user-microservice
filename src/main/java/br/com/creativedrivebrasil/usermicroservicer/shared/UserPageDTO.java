package br.com.creativedrivebrasil.usermicroservicer.shared;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDTO {

	private List<UserDTO> users = new ArrayList<>();
	private Integer pageSize;
	private Integer pageOffset;
	
}
