split(nil,K,nil,nil).
split(t((TKey, TValue),TY,TL,TR),K,SplitedLeft,SplitedRight) :-
 (  K > TKey
 -> split(TR,K,Temp1,SplitedRight), SplitedLeft = t((TKey, TValue),TY,TL,Temp1);
    split(TL,K,SplitedLeft,Temp2), SplitedRight = t((TKey, TValue),TY,Temp2,TR)
 ).

merge(A,nil,A).
merge(nil,B,B) :- B \= nil.

merge(t((AKey,AValue),AY,AL,AR),t((BKey,BValue),BY,BL,BR),Merged) :-
 (  AY > BY
 -> merge(AR,t((BKey,BValue),BY,BL,BR),Temp), Merged = t((AKey,AValue),AY,AL,Temp);
    merge(t((AKey,AValue),AY,AL,AR),BL,Temp), Merged = t((BKey,BValue),BY,Temp,BR)
 ).

new_node(I, t(I, Y, nil, nil)) :- rand_int(1000, Y).

insert((Key, Value), nil, R) :- new_node((Key, Value), R).

insert((Key, Value), t((TKey,TValue), TY, TL, TR), R) :-
      split(t((TKey,TValue), TY, TL, TR), Key, LeftTree, RightTree),
      new_node((Key, Value), Node),
      merge(LeftTree, Node, InsertedLeftT),
      merge(InsertedLeftT, RightTree, R).

map_remove(TreeMap, Key, R) :-
      split(TreeMap, Key, LeftTree, RightTree),
      split(RightTree, Key + 1, _, RemovedRightT),
      merge(LeftTree, RemovedRightT, R).

map_put(TreeMap, Key, Value, Result) :-
      map_remove(TreeMap, Key, R),
      insert((Key, Value), R, Result).

map_get(nil, not Key, _).
map_get(t((Key,TValue), _, _, _), Key, TValue).
map_get(t((TKey,TValue), TY, TL, TR), Key, R) :- Key \= TKey,
      (  Key > TKey
      -> map_get(TR, Key, R);
      map_get(TL, Key, R)).

map_replace(TreeMap, Key, Value, TreeMap) :- not map_get(TreeMap, Key, R).
map_replace(TreeMap, Key, Value, Result) :- map_get(TreeMap, Key, R), map_put(TreeMap, Key, Value, Result).

build([], T, T).
build([(Key, Value) | Rest], TBuilt, R) :-
    map_put(TBuilt, Key, Value, TNew),
    build(Rest, TNew, R).

tree_build(ListMap, TreeMap) :- build(ListMap, nil, TreeMap).

map_size(nil, 0).
map_size(t(_, TY, TL, TR), Size) :-
        map_size(TL, LSize),
        map_size(TR, RSize),
        Size is 1 + LSize + RSize.