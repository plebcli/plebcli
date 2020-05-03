#pragma once
#include <regex>
#include "properties.h"
#include "builder.h"

#define DA "da"
#define NE "ne"

#define VERSION 0.0.1

namespace pleb
{
	const std::regex VARIABLE_REGEX = std::regex("/definirai promenliva ([a-zA-Z][^\s]+) ravna na .*/");

	const std::regex FUNCTION_REGEX = std::regex("definirai funkciq ([a-zA-Z][^\s]+) s parametri \(([^,]*)(, ?([^,]*))*\) koqto pravi");

	const std::set<std::string> keywords = { "definirai", "promenliva", "funkciq", "ako", "to" };

	const std::set<std::string> operators = { "plius", "minus", "po", "deleno na", "e ravno na" };

	const static std::string CLI_PREVIEW = "pleb>";

	const static std::string RESULT = "Rezultata e ";

	void display(std::string msg);
	void display(std::string msg, std::runtime_error details);
	void display(std::string msg, std::exception details);

	class pleb_could_not_start : public std::exception
	{
	public:
		pleb_could_not_start(char const* const message) throw();
		pleb_could_not_start() throw();
	};

	// types
	using builder = Builder;
	using oper = Operator;
	using func = Function;
	using exp = Expression;
	using obj = Object;
	using variable = Variable;

	static inline void ltrim(std::string& s) {
		s.erase(s.begin(), std::find_if(s.begin(), s.end(), [](int ch) {
			return !std::isspace(ch);
			}));
	}

	// trim from end (in place)
	static inline void rtrim(std::string& s) {
		s.erase(std::find_if(s.rbegin(), s.rend(), [](int ch) {
			return !std::isspace(ch);
			}).base(), s.end());
	}

	// trim from both ends (in place)
	static inline void trim(std::string& s) {
		ltrim(s);
		rtrim(s);
	}

	// trim from start (copying)
	static inline std::string ltrim_copy(std::string s) {
		ltrim(s);
		return s;
	}

	// trim from end (copying)
	static inline std::string rtrim_copy(std::string s) {
		rtrim(s);
		return s;
	}

	// trim from both ends (copying)
	static inline std::string trim_copy(std::string s) {
		trim(s);
		return s;
	}
}