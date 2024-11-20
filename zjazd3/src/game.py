import random
from collections import deque


class GameBoard:
    def __init__(self, width=5, height=5):
        self.width = width
        self.height = height
        self.board = [[" " for _ in range(width)] for _ in range(height)]
        self.start = self._generate_edge_position()
        self.stop = self._generate_edge_position(avoid=self.start)
        self.obstacles = self._generate_obstacles()
        self.player_position = self.start
        self._place_elements()

    def _generate_edge_position(self, avoid=None):
        while True:
            if random.choice([True, False]):
                x = random.randint(0, self.width - 1)
                y = 0 if random.choice([True, False]) else self.height - 1
            else:
                x = 0 if random.choice([True, False]) else self.width - 1
                y = random.randint(0, self.height - 1)
            if avoid is None or (x, y) != avoid:
                return (x, y)

    def _generate_obstacles(self, count=5):
        obstacles = set()
        while len(obstacles) < count:
            x = random.randint(0, self.width - 1)
            y = random.randint(0, self.height - 1)
            if (x, y) not in [self.start, self.stop]:
                obstacles.add((x, y))
        return obstacles

    def _place_elements(self):
        self.board = [[" " for _ in range(self.width)] for _ in range(self.height)]
        self.board[self.start[1]][self.start[0]] = "A"
        self.board[self.stop[1]][self.stop[0]] = "B"
        for x, y in self.obstacles:
            self.board[y][x] = "X"
        px, py = self.player_position
        if self.player_position not in [self.start, self.stop]:
            self.board[py][px] = "P"

    def display(self):
        """Displays the board in terminal"""
        print("\n   " + " ".join(str(i) for i in range(self.width)))
        print("  +" + "--" * self.width + "+")
        for y, row in enumerate(self.board):
            print(f"{y} |" + " ".join(row) + "|")
        print("  +" + "--" * self.width + "+")

    def move_player(self, direction):
        """Moves player in chosen direction"""
        x, y = self.player_position
        if direction == "up":
            y -= 1
        elif direction == "down":
            y += 1
        elif direction == "left":
            x -= 1
        elif direction == "right":
            x += 1
        else:
            raise ValueError("Invalid direction. Use 'up', 'down', 'left', or 'right'.")

        if 0 <= x < self.width and 0 <= y < self.height and (x, y) not in self.obstacles:
            self.player_position = (x, y)
            self._place_elements()
            return True
        return False

    def is_path_possible(self):
        """Checks if there is a possible way form A to B"""
        start_x, start_y = self.start
        stop_x, stop_y = self.stop

        visited = set()
        queue = deque([(start_x, start_y)])

        while queue:
            x, y = queue.popleft()

            if (x, y) == (stop_x, stop_y):
                return True

            if (x, y) in visited:
                continue

            visited.add((x, y))

            for dx, dy in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
                nx, ny = x + dx, y + dy
                if 0 <= nx < self.width and 0 <= ny < self.height:
                    if (nx, ny) not in self.obstacles:
                        queue.append((nx, ny))

        return False


if __name__ == "__main__":
    game = GameBoard(5, 5)
    print("Start position (A) and stop position (B) are randomly generated.")
    print("Navigate the board using 'up', 'down', 'left', 'right'. Avoid obstacles (X).")
    print("Your goal is to reach the STOP position (B).\n")

    game.display()

    while game.player_position != game.stop:
        move = input("Move (up, down, left, right): ").strip().lower()
        if game.move_player(move):
            print("Move successful!")
        else:
            print("Invalid move! Try again.")
        game.display()

    print("Congratulations! You reached the STOP position.")
