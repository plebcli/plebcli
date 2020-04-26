#pragma once
#include "expression.h"

class Operator
{
	public:
		virtual ~Operator() {}
		virtual Object consume(Expression& exp1, Expression& exp2) = 0;
};

class Plius : public Operator
{
	public:
		Object consume(Expression& exp1, Expression& exp2);
};

class Minus : public Operator
{
	public:
		Object consume(Expression& exp1, Expression& exp2);
};

class Po : public Operator
{
	public:
		Object consume(Expression& exp1, Expression& exp2);
};

class Deleno : public Operator
{
	public:
		Object consume(Expression& exp1, Expression& exp2);
};

class InvalidOperator : public Operator
{
	public:
		Object consume(Expression& exp1, Expression& exp2);
};