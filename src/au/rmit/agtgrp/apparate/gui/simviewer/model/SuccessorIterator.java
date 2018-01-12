package au.rmit.agtgrp.apparate.gui.simviewer.model;

import au.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.rmit.ai.agtgrp.apparate.jpathplan.interfaces.NodeIterator;

public class SuccessorIterator implements NodeIterator {
	
	private GridCell    _parent = null;
	private GridCell    _current = null;
	private ICellEval	_view = null;
	private int         _currentIndex = 0;
	private int			_maxIndex = 0;
	private GridDomain                  _problem = null;


	public SuccessorIterator(ICellEval celleval, GridCell cNode,
			GridDomain gridDomain) {
		// TODO Auto-generated constructor stub
        _problem = gridDomain;
        _parent = cNode;
        _view = celleval;
        _maxIndex = _view.getMaxSuccessors();
 	}

	public GridCell next() {
		_current = null;
        for ( ; _currentIndex < _maxIndex; _currentIndex++ ) {
        	_current = _view.getNextSuccessor(_currentIndex, _parent, _problem);
        	if (_current == null) continue;
        	break;
        }
       if (!eoi()) _currentIndex++; 
       return _current;
   }

  public boolean eoi() {
	  return (_currentIndex >= _maxIndex);
  }
	
}
