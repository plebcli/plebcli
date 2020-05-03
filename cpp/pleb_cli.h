#pragma once
#include "pleb_std.h"
#include <vector>
#include <set>
#include <map>

class Token
{

};

class PlebCli {

	public:
		PlebCli();
		PlebCli(Properties<std::string, std::string> prop);
		~PlebCli();

	public:
		void start();

	private:
		void __initialize();
		bool cli_is_running();
		std::vector<std::string>& parse();
		bool validate(std::vector<std::string>& input);
		Token& tokenize(std::vector<std::string>& input);
		void respond(Token& token);
		void run();

		// register
		pleb::variable& register_variable(std::string variable_definition);

	private:
		bool debug;
		bool m_is_running;
		std::map<std::string, std::map<std::string, pleb::obj>> m_defined_objects;
};