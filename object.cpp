#include "object.h"

Object::Object(std::string data)
{
    m_data = stoi(data);
}

Object::~Object()
{
}

int Object::get_data() const
{
    return m_data;
}

Object& Object::operator+(const Object& other)
{
    this->m_data += other.m_data;
    return *this;
}

Object& Object::operator-(const Object& other)
{
    this->m_data -= other.m_data;
    return *this;
}

Object& Object::operator*(const Object& other)
{
    this->m_data *= other.m_data;
    return *this;
}

Object& Object::operator/(const Object& other)
{
    this->m_data /= other.m_data;
    return *this;
}