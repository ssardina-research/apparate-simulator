/**
 * A Library of Path Planning Algorithms
 *
 * Copyright (C) 2010 Andy Xie, Abhijeet Anand and Sebastian Sardina
 * School of CS and IT, RMIT University, Melbourne VIC 3000.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package au.edu.rmit.agtgrp.apparate.gui.simviewer.model;

import au.edu.rmit.agtgrp.apparate.gui.simviewer.model.listener.ICellEval;
import au.edu.rmit.agtgrp.apparate.jpathplan.interfaces.NodeIterator;

public class SuccessorIterator implements NodeIterator {
	
	private GridCell    _parent = null;
	private GridCell    _current = null;
	private ICellEval _view = null;
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
