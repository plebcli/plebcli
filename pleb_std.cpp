#include "pleb_std.h"

void pleb::display(std::string msg)
{
	std::cout << msg << std::endl;
}

void pleb::display(std::string msg, std::runtime_error details)
{
	std::cout << msg << std::endl;
	std::cout << details.what() << std::endl;
}

void pleb::display(std::string msg, std::exception details)
{
	std::cout << msg << std::endl;
	std::cout << details.what() << std::endl;
}

class pleb_could_not_start : public std::exception
{
	public:
		pleb::pleb_could_not_start(char const* const message) throw();
		pleb::pleb_could_not_start() throw();
};