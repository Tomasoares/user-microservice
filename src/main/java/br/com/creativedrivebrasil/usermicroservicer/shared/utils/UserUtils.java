package br.com.creativedrivebrasil.usermicroservicer.shared.utils;

import br.com.creativedrivebrasil.usermicroservicer.mongodb.documents.UserType;
import br.com.creativedrivebrasil.usermicroservicer.shared.UserTypeDTO;

public class UserUtils {

	public static Integer getUserTypeId(UserTypeDTO dto) {
		if (dto == null) {
			return null;
		}
		
		UserType findById = UserType.findById(dto.getId());
		
		return findById != null? findById.getId() : null;
	}

	public static UserTypeDTO getUserTypeDTO(Integer idUserType) {
		if (idUserType == null) {
			return null;
		}
		
		UserTypeDTO findById = UserTypeDTO.findById(idUserType);
		
		return findById;
	}
}
