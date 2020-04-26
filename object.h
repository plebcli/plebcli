#pragma once
#include <ostream>
#include <string>

class Object
{
public:
	Object(std::string data);
	~Object();

	int get_data() const;

	Object& operator+(const Object& other);
	Object& operator-(const Object& other);
	Object& operator*(const Object& other);
	Object& operator/(const Object& other);
private:
	// TODO for now work only with ints....
	int m_data;
};

//std::ostream& operator<<(std::ostream& os, const Object& obj)
//{
//	return os << obj.get_data();
//}