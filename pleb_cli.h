#pragma once
#include "pleb_std.h"
#include <vector>
#include <set>
#include <map>

class token
{

};

class PlebCli {

	public:
		PlebCli();
		PlebCli(properties<std::string, std::string> prop);
		~PlebCli();

	public:
		void start();

	private:
		void __initialize();
		bool cli_is_running();
		std::vector<std::string>& parse();
		bool validate(std::vector<std::string>& input);
		token& tokenize(std::vector<std::string>& input);
		void respond(token& token);
		void run();

	private:
		bool debug;
		bool m_is_running;
		std::map<std::string, std::map<std::string, pleb::obj>> m_defined_objects;
};