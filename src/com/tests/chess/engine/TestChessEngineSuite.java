package com.tests.chess.engine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestPieces.class, TestBoard.class, TestCastling.class })

public class TestChessEngineSuite {

}


/*
 * TODO:
 * test simple check mates
 * test simple stalemates
 * fix highlight legal moves bug, moving into check
 */