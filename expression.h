#pragma once
#include <string>
#include "object.h"

class Expression
{
private:
	std::string m_exp_str;
public:
	Expression(std::string exp_str);
	~Expression();

	Object evaluate();
};