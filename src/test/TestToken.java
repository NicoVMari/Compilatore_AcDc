package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.*;

class TestToken {

    @Test
    void testTokenCostructor() {
        Token token = new Token(TokenType.INT, 1, "123");
        assertEquals(1, token.getRiga());
        assertEquals(TokenType.INT, token.getTipo()); 
        assertEquals("123", token.getVal());

        token = new Token(TokenType.FLOAT, 2);
        assertEquals(2, token.getRiga());
        assertEquals(TokenType.FLOAT, token.getTipo());
        assertNull(token.getVal()); 
    }

    @Test
    void testToString() {
        Token token = new Token(TokenType.ID, 3, "x");
        assertEquals("<ID,r:3,x>", token.toString());

        token = new Token(TokenType.OP_ASSIGN, 4);
        assertEquals("<OP_ASSIGN,r:4>", token.toString());
    }

}
