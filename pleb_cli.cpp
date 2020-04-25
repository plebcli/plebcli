#include "pleb_cli.h"

PlebCli::PlebCli()
{
	// TODO twa sh se mahme s props
	this->debug = false;
	__initialize();
}

PlebCli::PlebCli(properties<std::string, std::string> prop)
{
	//load_props(prop);
	this->debug = true;
	__initialize();
}

PlebCli::~PlebCli()
{

}

void PlebCli::start()
{
	this->m_is_running = true;
	run();
}

void PlebCli::run()
{
	while (cli_is_running())
	{
		std::vector<std::string> input = parse();
		token t = tokenize(input);
		respond(t);
	}
	// TODO throw exception
}

void PlebCli::__initialize()
{
	this->m_is_running = false;
	this->m_defined_objects = std::map<std::string, std::map<std::string, pleb::obj>>();

	// loop through all keywords and create empty map inside the defined objects
	std::set<std::string>::iterator it = pleb::keywords.begin();
	while (it != pleb::keywords.end())
	{
		// for each keyword create empty map with key that keyword name
		m_defined_objects.insert(std::pair<std::string, 
								 std::map<std::string, pleb::obj>>(*it, std::map<std::string, pleb::obj>()));
		it++;
	}

	if (debug)
	{
		std::cout << "Defined keywords are: ";
		// print all keywords in the map
		for (std::map<std::string, std::map<std::string, pleb::obj>>::const_iterator it = m_defined_objects.begin();
			 it != m_defined_objects.end();
			 ++it)
		{
			std::cout << it->first << " ";
		}
		std::cout << std::endl;
	}
}

bool PlebCli::cli_is_running()
{
	return this->m_is_running;
}

bool PlebCli::validate(std::vector<std::string>& input) 
{
	if (debug)
	{
		std::cout << "Validating input..!" << std::endl;
	}

	// validate ( and )
	long long count = 0L;
	for (auto line : input)
	{
		for (std::string::size_type i = 0; i < line.size(); i++)
		{
			switch (line[i])
			{
				case '(': count++; break;
				case ')': count--; break;
				case '{': count--; break;
				case '}': count++; break;
			}
		}
	}

	if (debug)
	{
		std::cout << "Input validated: " << (count == 0 ? DA : NE) << std::endl;
	}
	
	return count == 0L;
}

std::vector<std::string>& PlebCli::parse()
{
	// init new vector which purpose is to store the input data in the same order as the user typed them
	std::vector<std::string>* input = new std::vector<std::string>();

	bool is_input_parsable = true;
	do
	{
		std::cout << pleb::CLI_PREVIEW;

		// read user input untill it becomes valid or there is error
		std::string current_line;
		if (std::getline(std::cin, current_line))
		{
			(*input).push_back(current_line);

			bool is_input_parsable = validate(*input);

			// the input is ready to be evaluated
			if (is_input_parsable)
			{
				break;
			}
		}
	} while (is_input_parsable);

	if (debug)
	{
		std::cout << "START" << std::endl;
		for (auto line : *input)
		{
			std::cout << line << std::endl;
		}
		std::cout << "END" << std::endl;
	}

	return *input;
}

token& PlebCli::tokenize(std::vector<std::string>& input)
{
	if (debug)
	{
		std::cout << "tokenizing..!" << std::endl;
	}

	bool result = false;

	for (auto line : input)
	{
		// check for predefined functions

		// check for predefined operators
		for (std::string op : pleb::operators)
		{
			// if the operator is in the middle of the expression then it is valid
			int op_idx = line.find(op);
			if (op_idx != std::string::npos)
			{
				if (op == "plius")
				{
					std::string left = pleb::trim_copy(line.substr(0, op_idx));
					std::string right = pleb::trim_copy(line.substr(op_idx + op.length(), line.length() - (op_idx + op.length())));

					int a = std::stoi(left);
					int b = std::stoi(right);

					std::cout << "resultata e " << (a + b) << std::endl;
					result = true;
				}
			}
		}
	}

	if (!result)
	{
		std::cout << "pishi na balgarski we" << std::endl;
	}

	token* t = new token();
	return *t;
}

void PlebCli::respond(token& token)
{
	//std::cout << pleb::RESULT << token.evaluate() << std::endl;
}