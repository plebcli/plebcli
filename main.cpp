#include "pleb_cli.h";

void main() 
{

	PlebCli cli = PlebCli();
	try 
	{
		cli.start();
	} catch (std::exception e)
	{
		pleb::display("Could not start cli", e);
	}
}