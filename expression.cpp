#include "expression.h";

Expression::Expression(std::string exp_str) : m_exp_str(exp_str)
{
}

Expression::~Expression()
{
}

Object Expression::evaluate()
{
	return Object(m_exp_str);
}