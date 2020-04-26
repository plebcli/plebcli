#include "builder.h"

Object& Builder::build_object(std::string data)
{
	return *(new Object(data));
}

Function& Builder::build_function(std::string func_name)
{
	return *(new Function());
}

Operator& Builder::build_operator(const std::string operator_name)
{
	Operator* result;

	if (operator_name == "minus")
	{
		result = new Minus();
	} 
	else if (operator_name == "plius")
	{
		result = new Plius();
	}
	else if (operator_name == "po")
	{
		result = new Po();
	}
	else if (operator_name == "deleno na")
	{
		result = new Deleno();
	}
	else
	{
		result = new InvalidOperator();
	}

	return *result;
}