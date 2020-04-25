#pragma once
#include <iostream>
#include <string>
#include <set>

template <class K, class V>
class properties {
public:
	properties(std::string filename);
	properties();
	//~properties();

	V get_property(K key) const;
	void set_property(K key, V value);
private:
	std::set<std::pair<K, V>> m_props;
};
