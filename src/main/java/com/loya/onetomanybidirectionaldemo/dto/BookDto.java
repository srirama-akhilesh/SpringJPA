package com.loya.onetomanybidirectionaldemo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BookDto {

	@NotEmpty(message= "Id cannot be empty")
	private Long id;
	
	@NotNull(message= "Title cannot be empty")
	public String title;
	
	@Pattern(regexp= "[a-zA-Z]",message= "Genre cannot have numericals")
	public String genre;
	
}
