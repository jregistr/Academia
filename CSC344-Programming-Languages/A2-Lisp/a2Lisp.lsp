; (setq p1 '(+ x (+ x (+ y (+ z 2)))))
; (setq p2 '(+ (+ z 2) (+ x 5)))
; (setq p3 '(+ 1 a))

(setq p1 '(+ x (* x (- y (/ z 2)))))
(setq p2 '(+ (- z 2) (* x 5)))
(setq p3 '(+ 1 a))

(defun addexp (e1 e2) (list '+ e1 e2))
(defun subexp (e1 e2) (list '- e1 e2))
(defun mulexp (e1 e2) (list '* e1 e2))
(defun divexp (e1 e2) (list '/ e1 e2))

(defun deep-subst (old new l)
  (cond
   ((null l) 
     nil
   )
   ((listp (first l))
    (cons (deep-subst old new (first l)) (deep-subst old new (rest l)))
   )
   ((eq old (first l)) 
    (cons new (deep-subst old new (rest l)))
   )
   (T   
    (cons (first l) (deep-subst old new (rest l)))
   )
  )
)

(defun subst-bindings (bindinglist exp)
    (cond 
        ( (null bindinglist) 
           exp 
		 )
        (T
           ;((deep-subst (first (first bindinglist)) (rest (first bindinglist)) exp))
		   (deep-subst (first(first bindinglist)) (first (rest(first bindinglist))) (subst-bindings (rest bindinglist) exp))
        )
    )
)

(defun simplify-triple(op left-arg right-arg)
  (cond
    ((and (numberp left-arg) (numberp right-arg))
      (eval (list op left-arg right-arg))
    )
    ((and (eq op '+) (eql right-arg 0))
      left-arg
    )
	
	((and (eq op '+) (eql left-arg 0))
		right-arg
	)
	
	((and (eq op '-) (eql right-arg 0))
		left-arg
	)
	
	((and (eq op '-) (eql right-arg left-arg))
		0
	)
	
	((and (eq op '*) (eql right-arg 0))
		0
	)
	
	((and (eq op '*) (eql left-arg 0))
		0
	)
	
	((and (eq op '*) (eql left-arg 1))
		right-arg
	)
	
	((and (eq op '*) (eql right-arg 1))
		left-arg
	)
	
	((and (eq op '/) (eql left-arg 0))
		0
	)
	
	((and (eq op '/) (eql right-arg 1))
		left-arg
	)
	
	((and (eq op '/) (eql left-arg right-arg))
		1
	)
	
	; ( (and (eq op '+) (and (eq (first right-arg) '-) (eql (first (rest right-arg)) 0))) 
		; 0
	; )
	
	( (and (eq op '+) (listp right-arg) (eq (first right-arg) '-) (eql (first (rest right-arg)) 0)) 
		0
	)
	
	(T
		(list op left-arg right-arg)
	)
  )
)


(defun simplify (exp)
  (cond
    ( (listp exp)
        (simplify-triple (first exp) (simplify (first (rest exp))) (simplify (first (rest (rest exp)))))
  ) 
  (T 
        exp
	)
  )
)

(defun evalexp (bindinglist exp)
  (simplify (subst-bindings bindinglist exp)
))


; (defun simplify (exp)
  ; (cond
		; ( (listp exp)
			; (simplify-triple (first exp) (simplify (first (rest exp))) (simplify (first (rest (rest exp)))))
		; )	
		; (T 
			; exp
		; )
   ; )
; )

; (defun evalexp (bindinglist exp)
  ; (simplify (subst-bindings (bindinglist exp)))
	; ;(subst-bindings exp bindinglist)
; )