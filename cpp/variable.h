#pragma once
#include <string>
#include "object.h"

class Variable : public Object
{
	public:
		Variable(std::string var_name, std::string var_value);
		~Variable();
		std::string get_name() const;
		std::string get_value() const;

	private:
		std::string m_var_name;
		std::string m_var_value;
};