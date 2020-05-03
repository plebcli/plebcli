#pragma once
#include "operator.h"
#include "function.h"

class Builder
{
public:
	Object& build_object(std::string data);
	Function& build_function(std::string func_name);
	Operator& build_operator(std::string operator_name);
};