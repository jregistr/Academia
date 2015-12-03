class(vehicle).
class(motorcycle).

type(spentOnGas,int).
type(boughtFor,float).

hasMember(vehicle,spentOnGas).
hasMember(vehicle,boughtFor).

hasMethod(vehicle,totalSpent).

hasParameter(totalSpent,int).

isSubClassOf(motorcycle,vehicle).

					
hasMethod(X,Y) :-
				class(X),isSubClassOf(X,Z),hasMethod(Z,Y).
					

hasMember(A,B) :- class(A),
					isSubClassOf(A,C),
						hasMember(C,B).
					
totalSpent(X,float) :- integer(X),hasMethod(vehicle,totalSpent).					
