public VoxelShape makeShape(){
	VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0, 0.1875, 0.8125, 0.0625, 0.8125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.1875, 0.75, 0.1875, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.1875, 0.25, 0.6875, 0.375, 0.6875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.375, 0.3125, 0.625, 0.5625, 0.5625));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.5625, 0.375, 0.5625, 0.625, 0.5));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.40625, 0.625, 0.4375, 0.53125, 0.6875, 0.5));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.53125, 0.625, 0.4375, 0.65625, 0.6875, 0.5));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.28125, 0.625, 0.4375, 0.40625, 0.6875, 0.5));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.71875, 0.575, 0.44999999999999996, 1.0375, 0.6375, 0.49374999999999997));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.10000000000000009, 0.6125, 0.44999999999999996, 0.21875, 0.675, 0.49374999999999997));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.0625, 0.75, 0.25, 0.125, 0.8125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.0625, 0.75, 0.3125, 0.125, 0.8125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.0625, 0.75, 0.375, 0.125, 0.8125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.1875, 0.6875, 0.25, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.1875, 0.6875, 0.3125, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.1875, 0.625, 0.25, 0.25, 0.6875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.3125, 0.1875, 0.6875, 0.375, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.1875, 0.6875, 0.4375, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875, 0.1875, 0.5625, 0.25, 0.25, 0.625));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.1875, 0.6875, 0.75, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.1875, 0.6875, 0.6875, 0.25, 0.75));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.1875, 0.625, 0.75, 0.25, 0.6875));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.1875, 0.25, 0.75, 0.25, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.6875, 0.1875, 0.1875, 0.75, 0.25, 0.25));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.1875, 0.1875, 0.6875, 0.25, 0.25));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.375, 0.25, 0.6875, 0.4375, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.375, 0.25, 0.625, 0.4375, 0.3125));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.625, 0.375, 0.3125, 0.6875, 0.4375, 0.375));

	return shape;
}