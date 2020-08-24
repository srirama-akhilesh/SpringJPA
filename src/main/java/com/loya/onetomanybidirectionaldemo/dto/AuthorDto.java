package com.loya.onetomanybidirectionaldemo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AuthorDto {

	
	@NotNull(message= "Id cannot be null")
	private Long id;
	
	@NotEmpty(message= "Name cannot be empty")
	@NotNull(message= "Name cannot be null")
	@Length(min=1, max=6)
	public String firstName;
	
	@Length(min=1, max=2)
	@Pattern(regexp= "[a-zA-Z]", message= "Last name should contains only characters.No numericals")
	public String lastName;
	
}
