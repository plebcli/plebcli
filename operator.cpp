#include "operator.h"

Object Plius::consume(Expression& exp1, Expression& exp2)
{
	return Object(exp1.evaluate() + exp2.evaluate());
}

Object Minus::consume(Expression& exp1, Expression& exp2)
{
	return Object(exp1.evaluate() - exp2.evaluate());
}

Object Po::consume(Expression& exp1, Expression& exp2)
{
	return Object(exp1.evaluate() * exp2.evaluate());
}

Object Deleno::consume(Expression& exp1, Expression& exp2)
{
	return Object(exp1.evaluate() / exp2.evaluate());
}

Object InvalidOperator::consume(Expression& exp1, Expression& exp2)
{
	return Object("-1");
}