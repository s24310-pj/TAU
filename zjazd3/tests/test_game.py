import unittest
from src.game import GameBoard


class TestGameBoard(unittest.TestCase):

    def setUp(self):
        """Initialize the game board before each test"""
        self.game = GameBoard(width=5, height=5)

    def test_start_position(self):
        """Testing that the start position is correct"""
        self.assertEqual(self.game.player_position, self.game.start, "Player starts in wrong position")

    def test_stop_position(self):
        """Testing if the Stop position is different from the start position"""
        self.assertNotEqual(self.game.start, self.game.stop, "START and STOP positions are the same")

    def test_obstacles_not_on_start_or_stop(self):
        """Testing if the obstacles don't interfere with START and STOP positions"""
        self.assertNotIn(self.game.start, self.game.obstacles, "Obstacle is in the START position")
        self.assertNotIn(self.game.stop, self.game.obstacles, "Obstacle is in the STOP position")

    def test_player_cannot_move_outside_board(self):
        """Testing if player cannot move over gameboard"""
        self.game.player_position = (0, 0)
        self.assertFalse(self.game.move_player("up"), "Player should not move up, over the border of the board")
        self.assertFalse(self.game.move_player("left"), "Player should not move left, over the border of the board")

        self.game.player_position = (4, 4)
        self.assertFalse(self.game.move_player("down"), "Player should not move down, over the border of the board")
        self.assertFalse(self.game.move_player("right"), "Player should not move right, over the border of the board")

    def test_player_cannot_move_into_obstacle(self):
        """Testing if player cannot move to obstacle"""
        obstacle = next(iter(self.game.obstacles))
        self.game.player_position = (obstacle[0] - 1, obstacle[1])
        self.assertFalse(self.game.move_player("right"), "Player should not move into obstacle")

    def test_player_can_move_on_empty_field(self):
        """Testing if player can properly move on empty field"""
        self.game.player_position = (1, 1)
        self.assertTrue(self.game.move_player("right"), "Player can't move onto empty field")
        self.assertEqual(self.game.player_position, (2, 1), "Player position is incorrect")

    def test_elements_on_board(self):
        """Testing if START and STOP and obstacles are properly placed on board"""
        start_x, start_y = self.game.start
        stop_x, stop_y = self.game.stop

        self.assertEqual(self.game.board[start_y][start_x], "A", "START position is incorrect")
        self.assertEqual(self.game.board[stop_y][stop_x], "B", "STOP position is incorrect")

        for x, y in self.game.obstacles:
            self.assertEqual(self.game.board[y][x], "X", "Obstacles are incorrectly placed on board")

    def test_player_reaches_stop(self):
        """Testing if player reaching the STOP position ends the game"""
        self.game.player_position = self.game.stop
        self.assertEqual(self.game.player_position, self.game.stop, "Player reached the STOP field")

    def test_no_path_to_stop(self):
        """Testing case where there is no possible way form Start to STOP"""
        start_x, start_y = self.game.start

        potential_obstacles = [
            (start_x - 1, start_y), (start_x + 1, start_y),
            (start_x, start_y - 1), (start_x, start_y + 1)
        ]
        self.game.obstacles = {
            (x, y) for x, y in potential_obstacles if 0 <= x < self.game.width and 0 <= y < self.game.height
        }

        self.game._place_elements()

        self.assertFalse(self.game.is_path_possible(), "There should be no path possible")


if __name__ == "__main__":
    unittest.main()
