/*Prolog type checker program*/
class(vehicle).
class(motorcycle).

type(spentOnGas,int).
type(boughtFor,float).

hasMember(vehicle,spentOnGas).
hasMember(vehicle,boughtFor).

hasMethod(vehicle,totalSpent).

hasParameter(totalSpent,int).

isSubClassOf(motorcycle,vehicle).


/*hasMethod(X,Y) :- isSubClassOf(X,vehicle),hasMethod(vehicle,Y).*/
					
hasMethod(X,Y) :-
				class(X),isSubClassOf(X,Z),hasMethod(Z,Y).
					

/*hasMember(A,B) :-isSubClassOf(A,vehicle),hasMember(vehicle,B).*/

hasMember(A,B) :- class(A),
					isSubClassOf(A,C),
						hasMember(C,B).
					
totalSpent(X,float) :- integer(X),hasMethod(vehicle,totalSpent).					

hasType(V, M, A, R).