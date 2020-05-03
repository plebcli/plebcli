#include "variable.h"

Variable::Variable(std::string var_name, std::string var_value) : m_var_name(var_name), m_var_value(var_value), Object(var_value)
{
}

Variable::~Variable()
{
}
std::string Variable::get_name() const
{
	return m_var_name;
}
std::string Variable::get_value() const
{
	return m_var_value;
}
