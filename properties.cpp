#include "properties.h"

template <class K, class V>
properties<K, V>::properties(std::string filename)
{
	// read file and load properties
}

template <class K, class V>
properties<K, V>::properties()
{
	// read file and load properties
}

//template <class K, class V>
//properties<K, V>::~properties()
//{
//
//}

template <class K, class V>
V properties<K, V>::get_property(K key) const
{
	return this->m_props.find(key);
}

template <class K, class V>
void properties<K, V>::set_property(K key, V value)
{

}
