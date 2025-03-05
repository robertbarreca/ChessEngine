package com.tests.chess.engine;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestPieces.class,
        TestBoard.class,
        TestCastling.class,
        TestCheckmate.class,
        TestStalemate.class,
        TestFen.class })

public class TestChessEngineSuite {

}
